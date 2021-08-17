const express = require("express");
const { PORT } = require("./config");
const { rentAuthMiddleware } = require("./middlewares/auth.middleware");
const { authMiddleware } = require("./middlewares/auth.middleware");
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/auth", require("./routes/auth.router"));
app.use("/rent", authMiddleware, require("./routes/rent.router"));
//여기는 이제 헬멧박스 open, 후방감지기능, 반납 기능 포함하는 라우터 1개(or 3개로 나눠서) 만들어야함(꼭 rentAuthMiddleware 거치게 만들기. )
//app.use("/boxOpen", authMiddleware, rentAuthMiddleware, require("./routes/boxOpen.router"));

//error handling
// app.use((err, req, res, next) => {
//     res.status(500).json({ success: false, error: err.toString() });
// });

app.get("/", (req, res) => {
    res.send("hi");
});

app.listen(PORT, (err) => {
    if (err) {
        console.error(err);
        process.exit();
    } else console.log("서버 시작");
});
