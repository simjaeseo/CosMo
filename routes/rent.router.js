const express = require("express");
const { getConn } = require("../database/index.js");
const router = express.Router();

router.post("/macAddrPull", async (req, res) => {
    // 스캔한 qrcode 정보를 "대여하기" 버튼 누를 시 서버에 전송
    // body에 보낼지 쿼리스트링에 보낼지 뭐로 해야하지?

    console.log("맥주소 조회 기능을 시도합니다");
    const { qrcode } = req.body;

    let conn, result;
    try {
        conn = await getConn();
        const [[DBqrcode]] = await conn.query(`SELECT *  FROM helmetbox WHERE QRnumber = ?`, [qrcode]);

        if (DBqrcode) {
            console.log(DBqrcode.macAddress);
            result = { success: true, message: "조회 성공", macAddress: DBqrcode.macAddress };
        } else result = { success: false, message: "다시 인식해주세요" };
    } catch (e) {
        console.error(e);
        result = { success: false, error: err.toString() };
    } finally {
        if (conn) conn.release();
        if (result.success) res.json(result);
        else res.json(result);
    }
});

//대여하기 버튼 눌렀을때의 라우터
//사용자가 로그인했는지 인증하는 미들웨어 거친 후 작동하게 만들기.
router.post("/rent", async (req, res) => {
    // 스캔한 qrcode 정보를 "대여하기" 버튼 누를 시 서버에 전송
    // body에 보낼지 쿼리스트링에 보낼지 뭐로 해야하지?

    console.log("대여하기 기능을 시도합니다");
    const { qrcode } = req.body;

    let conn, result;
    try {
        conn = await getConn();
        const [[DBqrcode]] = await conn.query(`SELECT *  FROM helmetbox WHERE QRnumber = ?`, [qrcode]);

        if (DBqrcode) {
            // 대여하는 상황
            // 해당 QR코드에 맞는 라즈베리파이에 연결하여 센서제어하기. ---> 어떻게 연결? Flask?

            //대여중인 헬멧박스일 때
            if (DBqrcode.boxRentStatus == "true") {
                result = { success: false, message: "이미 대여중인 헬멧박스입니다." };
            } else {
                //신고처리 안된 헬멧박스
                if (DBqrcode.reportStatus == "false") {
                    // helmetbox 테이블의 boxRentStatus, users 테이블의 rentStatus 모두 true로 바꾸기
                    let status = "true";
                    // await conn.execute("UPDATE users set rentStatus = ? WHERE id = ?", [status, res.locals.user.id]);
                    await conn.execute("UPDATE helmetbox set boxRentStatus = ? WHERE id = ?", [status, DBqrcode.id]);
                    result = { success: true, message: "대여 성공" };
                }
                //신고처리 되어 사용불가능한 헬멧박스
                else {
                    result = { success: false, message: "현재 점검중인 헬멧박스로 아용이 불가합니다." };
                }
            }
        } else result = { success: false, message: "다시 인식해주세요" };
    } catch (e) {
        console.error(e);
        result = { success: false, error: err.toString() };
    } finally {
        if (conn) conn.release();
        if (result.success) res.json(result);
        else res.json(result);
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
