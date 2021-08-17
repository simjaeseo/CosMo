const express = require("express");
const router = require("express").Router();

router.get("/", (req, res) => {
    // 라즈베리파이에 요청
    res.send("boxOpen");
});

module.exports = router;
