import { Link } from "react-router-dom";

function Sidebar() {
    return (
        <aside>
            <h2>Financial CMS</h2>

            <nav>
                <Link to="/dashboard">
                    Dashboard
                </Link>

                <Link to="/topics">
                    Topics
                </Link>

                <Link to="/subtopics">
                    Subtopics
                </Link>

                <Link to="/lessons">
                    Lessons
                </Link>

                <Link to="/quizzes">
                    Quizzes
                </Link>
            </nav>
        </aside>
    );
}

export default Sidebar;