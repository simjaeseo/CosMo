const express = require("express");
const { getConn } = require("../database/index.js");
const router = express.Router();

router.post("/report", async (req, res) => {
    console.log(res.locals.user.nickname + "님이 신고하기를 통해 헬멧박스를 반납하였습니다.");
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
        await conn.execute("UPDATE helmetbox set boxRentStatus = ?  WHERE QRnumber = ?", ["false", QRnumber]);
        await conn.execute("UPDATE helmetbox set reportStatus = ? WHERE QRnumber = ?", ["true", QRnumber]);

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
