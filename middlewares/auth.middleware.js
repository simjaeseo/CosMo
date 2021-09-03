const jwt = require("jsonwebtoken");
const { JWT_SECRET } = require("../config/index.js");
const { getConn } = require("../database/index.js");

// rentStatus가 true인지 아닌지 검증하는 미들웨어를 만들어야함!!

module.exports = {
    //사용자가 로그인을 했는지 안했는지 검증하는 미들웨어
    authMiddleware: async (req, res, next) => {
        const token = req.headers.authorization;
	
        let conn;

        // 클라이언트의 request에 토큰이 존재하지 않을 경우
        if (!token) {
            res.status(401).json({ success: false, message: "토큰이 존재하지않습니다." });
        } else {
            const accessToken = token.split(" ");
            // 토큰을 " " 기준으로 나눴을때 첫번째 값이 Bearer가 아닐때
            if (accessToken[0] !== "Bearer") {

                res.status(401).json({ success: false });
            } else {
                try {
                    conn = await getConn();

                    const { userId } = jwt.verify(accessToken[1], JWT_SECRET);
		    
                    //locals라는 공간에 user를 저장하기 위한 행동들(즉, 사용자정보를 이 미들웨어를 거치는 라우터에서는 쉽게 쓸수있음!)
                    const [[user]] = await conn.query("SELECT * FROM users WHERE id = ?", [userId]);
		   
                    res.locals.user = user;
                    console.log(res.locals.user);
                } catch (e) {
                    console.error(e);
                    return;
                } finally {
                    if (conn) conn.release();
                    next();
                }
            }
        }
    },

    // 사용자가 헬멧박스를 대여했는지 안했는지 검증하는 미들웨어(근데 어차피 버튼 비활성화해놔서 구별될텐데 굳이 해야하나..?)
    // users 테이블의 rentStatus 모두 true인지 아닌지 검증(helmetbox 테이블의 boxRentStatus는 검증할 필요 없을듯(다른 유저가 이 헬멧박스를 대여하지 못하도록 상태를 설정해놓는 용도이니))
    // authMiddleware 한번 거치고 이 미들웨어 거치도록 했음. 이거때문에 오류 생일수도있음(프론트가 서버로 요청보낼때 토큰을 자동으로 보내주는게 아니라면 ?)
    rentAuthMiddleware: (req, res, next) => {
        console.log(res.locals.user.id);
        if (res.locals.user.rentStatus == "true") {
            console.log(1);
            next();
        } else {
            console.log(2);
            res.status(401).json({ success: false, message: "헬멧박스를 대여한 상태가 아닙니다." });
        }
    },
};
