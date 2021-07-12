const express = require("express");
const jwt = require("jsonwebtoken");
const { JWT_SECRET } = require("../config");
const { getConn } = require("../database/index.js");
const router = express.Router();

// 로그인 한 상태

// qr코드로 대여하기 요청

// QR코드가 db에 있는지 확인 후 있으면
// 유저의 대여상태를 true로 변경

// 그 이후 기능들을 대여상태 확인 미들웨어를 통해 권한을 줌

//=====================================================
//대여하기 버튼 눌렀을때의 라우터
//사용자가 로그인했는지 인증하는 미들웨어 거친 후 작동하게 만들기.
router.post("/", async (req, res) => {
    // 스캔한 qrcode 정보를 "대여하기" 버튼 누를 시 서버에 전송
    // body에 보낼지 쿼리스트링에 보낼지 뭐로 해야하지?
    const { qrcode } = req.body;

    let conn, result;
    try {
        conn = await getConn();
        const [[DBqrcode]] = await conn.query(`SELECT *  FROM helmetbox WHERE QRnumber = ?`, [qrcode]);

        if (DBqrcode) {
            console.log(DBqrcode);
            // 대여하는 상황
            // 해당 QR코드에 맞는 라즈베리파이에 연결하여 센서제어하기. ---> 어떻게 연결?

            // helmetbox 테이블의 boxRentStatus, users 테이블의 rentStatus 모두 true로 바꾸기
            let status = "true";
            await conn.execute("UPDATE users set rentStatus = ? WHERE id = ?", [status, res.locals.user.id]);
            await conn.execute("UPDATE helmetbox set boxRentStatus = ? WHERE id = ?", [status, DBqrcode.id]);

            result = { success: true, message: "대여 성공" };
        } else result = { success: false, message: `다시 인식해주세요` };
    } catch (e) {
        console.error(e);
        result = { success: false, error: err.toString() };
    } finally {
        if (conn) conn.release();
        if (result.success) res.status(201).json(result);
        else res.status(409).json(result);
    }
});

//헬멧박스 DB에 데이터 넣기
// (async () => {
//     let conn, result;

//     conn = await getConn();
//     await conn.execute("INSERT INTO helmetbox (QRnumber, boxRentStatus) VALUES (?,?)", ["hm001", "false"]);

//     if (conn) conn.release();
// })();

module.exports = router;
