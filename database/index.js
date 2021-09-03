const db = require("mysql2/promise");
const { DB_HOST, DB_USER, DB_PASSWORD } = require("../config/index.js");

const pool = db.createPool({
    host: "jaeseo2.c9gplmo8pgi2.ap-northeast-2.rds.amazonaws.com",
    user: "admin",
    password: "xmdlsk12",
    database: "CosMo",
    waitForConnections: true,
    connectionLimit: 5,
});

// // 테이블 만들기
// (async () => {
//     const conn = await pool.getConnection();
//     await conn.execute(`CREATE TABLE users(
//         id int(11) NOT NULL AUTO_INCREMENT,
//         nickname varchar(11) NOT NULL,
//         name varchar(11) NOT NULL,
//         password varchar(100) NOT NULL,
//         rentStatus varchar(100) NOT NULL,
//         created_at timestamp NOT NULL DEFAULT current_timestamp(),
//         updated_at timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
//         PRIMARY KEY (id)
//     ) DEFAULT CHARSET=utf8
//   `);
//     console.log("end");
// })();

// (async () => {
//     const conn = await pool.getConnection();
//     await conn.execute(`CREATE TABLE helmetbox(
//         id int(11) NOT NULL AUTO_INCREMENT,
//         QRnumber varchar(10) NOT NULL,
//         boxRentStatus varchar(10) NOT NULL,
//         created_at timestamp NOT NULL DEFAULT current_timestamp(),
//         updated_at timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
//         PRIMARY KEY (id)
//     ) DEFAULT CHARSET=utf8
//   `);
//     console.log("end");
// })();

// (async () => {
//     const conn = await pool.getConnection();
//     await conn.execute(`CREATE TABLE helmetbox(
//         id int(11) NOT NULL AUTO_INCREMENT,
//         QRnumber varchar(10) NOT NULL,
//         boxRentStatus varchar(10) NOT NULL,
//         created_at timestamp NOT NULL DEFAULT current_timestamp(),
//         updated_at timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
//         PRIMARY KEY (id)
//     ) DEFAULT CHARSET=utf8
//   `);
//     console.log("end");
// })();

module.exports = {
    getConn: () => pool.getConnection(),
};
