const express = require("express");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");
const { JWT_SECRET } = require("../config");
const { getConn } = require("../database/index.js");
const router = express.Router();

//회원가입 라우터
//테스트 완료(API 작동 정상)
router.post("/register", async (req, res) => {
    const { nickname, password } = req.body;
    console.log("회원가입을 시도합니다");
    if (nickname == null || password == null) res.status(400).json({ success: false, message: "아이디 또는 비밀번호를 입력해주세요" });
    else {
        let conn;
        let result;
        try {
            conn = await getConn();
            const [[user]] = await conn.query("SELECT * FROM users WHERE nickname = ?", [nickname]);
            if (user) result = { success: false, message: "닉네임이 이미 존재합니다." };
            else {
                const hashPw = await bcrypt.hash(password, await bcrypt.genSalt(12));
                await conn.execute("INSERT INTO users (nickname, password, rentStatus) VALUES (?,?,?)", [nickname, hashPw, "false"]);
                result = { success: true, message: "가입 완료" };
            }
        } catch (e) {
            console.error(e);
            result = { success: false, error: err.toString() };
        } finally {
            if (conn) conn.release();

            if (!result.success) res.status(409).json(result);
            else {
                res.json(result);
            }
        }
    }
});

//로그인 라우터
//테스트 완료(API 작동 정상)
router.post("/login", async (req, res) => {
    const { nickname, password } = req.body;
    console.log("로그인을 시도합니다.");
    try {
        conn = await getConn();
        const [[user]] = await conn.query(`SELECT *  FROM users WHERE nickname = ?`, [nickname]);
        if (!user) result = { success: false, message: "존재하지 않는 닉네임입니다." };
        else {
            if (await bcrypt.compare(password, user.password)) {
                //토큰 발급
                const token = jwt.sign({ userId: user.id }, JWT_SECRET, { expiresIn: "365d" });
                result = { success: true, message: "로그인 성공", token };
            } else result = { success: false, message: "비밀번호를 다시 입력해주세요" };
        }
    } catch (e) {
        console.error(e);
        result = { success: false, message: "에러가 발생했습니다." };
    } finally {
        if (conn) conn.release();
        if (result.success) res.status(201).json(result);
        else res.status(409).json(result);
    }
});

//회원가입 라우터
//테스트 완료(API 작동 정상)
router.post("/overlapCheck", async (req, res) => {
    const { nickname } = req.body;
    console.log("중복확인을 시도합니다.");
    try {
        conn = await getConn();
        const [[user]] = await conn.query(`SELECT *  FROM users WHERE nickname = ?`, [nickname]);
        if (!user) result = { success: true, message: "가입 가능한 아이디입니다." };
        else {
            result = { success: false, message: "아이디가 이미 존재합니다." };
        }
    } catch (e) {
        console.error(e);
        result = { success: false, message: "에러가 발생했습니다." };
    } finally {
        if (conn) conn.release();
        if (result.success) res.status(201).json(result);
        else res.status(409).json(result);
    }
});

module.exports = router;
