const express = require("express");
const { getConn } = require("../database/index.js");
const router = express.Router();

router.post("/report", async (req, res) => {
    console.log("신고하기 기능을 시도합니다");
    const { QRnumber, content, helmetStatus } = req.body;

    let conn, result;
    try {
        conn = await getConn();

        //이미 값이 존재하면 update로 해야겠다...

        await conn.execute("INSERT INTO report (nickname, name, QRnumber, content, helmetStatus, repair) VALUES (?,?,?,?,?,?)", [
            res.locals.user.nickname,
            res.locals.user.name,
            QRnumber,
            content,
            helmetStatus,
            "false",
        ]);
        // await conn.execute("UPDATE helmetbox set boxRentStatus = ? WHERE id = ?", [status, DBqrcode.id]); 업데이트하기!!! + 123반납에러는 뭐지?
        await conn.execute("UPDATE helmetbox set (boxRentStatus, reportStatus) VALUES (?,?) WHERE id = ?", ["false", "true, QRnumber"]);
        // await conn.execute("INSERT INTO helmetbox (boxRentStatus, reportStatus) VALUES (?,?)", ["false", "true"]);

        result = { success: true, message: "신고 처리 되었습니다." };
    } catch (e) {
        console.error(e);
        result = { success: false, message: "에러가 발생했습니다." };
    } finally {
        if (conn) conn.release();
        if (result.success) res.json(result);
        else res.json(result);
    }
});

module.exports = router;
