import { NavLink, useNavigate } from "react-router-dom";
import authService from "../services/authService";
import "./Sidebar.css";

function Sidebar() {

    const navigate = useNavigate();

    const handleLogout = () => {

        authService.logout();

        navigate("/login");
    };

    return (
        <aside className="sidebar">

            <div className="sidebar-header">
                <h2>Content Management Portal</h2>
            </div>

            <nav>

                <NavLink to="/">
                    Dashboard
                </NavLink>

                <NavLink to="/topics">
                    Topics
                </NavLink>

                <NavLink to="/subtopics">
                    Subtopics
                </NavLink>

                <NavLink to="/lessons">
                    Lessons
                </NavLink>

                <NavLink to="/quizzes">
                    Quizzes
                </NavLink>

                <NavLink to="/study-chains">
                    Study Chains
                </NavLink>

            </nav>

            <button
                className="logout-button"
                onClick={handleLogout}
            >
                Logout
            </button>

        </aside>
    );
}

export default Sidebar;