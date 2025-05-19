import {Link} from "react-router-dom";
import {useUser} from "../context/UserContext"
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";

function AppNavbar() {
    const { state, logout } = useUser();

    return (
        <AppBar position="static" color="default" elevation={1}>
            <Toolbar sx={{ justifyContent: "space-between" }}>
                UngBoard
                <Typography
                    variant="h6"
                    component={Link}
                    to="/"
                    sx={{ textDecoration: "none", color: "inherit" }}
                >
                    📝 MyBoard
                </Typography>

                {/* 오른쪽: 로그인 상태에 따라 */}
                <Box>
                    {state ? (
                        <>
                            <Typography variant="body1" sx={{ display: "inline", mr: 2 }}>
                                {state.nickname} ({state.role})
                            </Typography>
                            <Button variant="outlined" color="error" onClick={logout}>
                                Logout
                            </Button>
                        </>
                    ) : (
                        <>
                            <Button
                                component={Link}
                                to="/sign-in"
                                variant="outlined"
                                sx={{ mr: 1 }}
                            >
                                로그인
                            </Button>
                            <Button
                                component={Link}
                                to="/sign-up"
                                variant="contained"
                                color="primary"
                            >
                                회원가입
                            </Button>
                        </>
                    )}
                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default AppNavbar