const express = require("express");
const { getConn } = require("../database/index.js");
const router = express.Router();

router.post("/return", async (req, res) => {
    console.log("반납하기 기능을 시도합니다");
    const { QRnumber } = req.body;

    let conn, result;
    try {
        conn = await getConn();
        const [[DBqrcode]] = await conn.query(`SELECT *  FROM helmetbox WHERE QRnumber = ?`, [QRnumber]);

        let status = "false";
        await conn.execute("UPDATE helmetbox set boxRentStatus = ? WHERE id = ?", [status, DBqrcode.id]);

        result = { success: true, message: "반납 처리 되었습니다." };
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
