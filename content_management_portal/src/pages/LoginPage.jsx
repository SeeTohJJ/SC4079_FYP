import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../services/authService";

function LoginPage() {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        setError("");

        try {
            await authService.login(email, password);
            navigate("/dashboard");
        } catch (error) {
            console.error(error);
            setError("Invalid email or password.");
        }
    };

    return (
        <div>
            <h1>Financial Literacy CMS</h1>

            <form onSubmit={handleLogin}>
                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>

                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                {error && <p>{error}</p>}

                <button type="submit">
                    Login
                </button>
            </form>
        </div>
    );
}

export default LoginPage;