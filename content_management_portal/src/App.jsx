import {
    BrowserRouter,
    Routes,
    Route,
    Navigate,
} from "react-router-dom";

import AdminLayout from "./layouts/AdminLayout";

import LoginPage from "./pages/login/LoginPage";
import DashboardPage from "./pages/dashboard/DashboardPage";

import TopicsPage from "./pages/topics/TopicsPage";
import SubtopicsPage from "./pages/subtopics/SubtopicsPage";
import LessonsPage from "./pages/lessons/LessonsPage";

function ProtectedRoute({ children }) {

    const token =
        localStorage.getItem("adminToken");

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return children;
}

function App() {

    return (
        <BrowserRouter>

            <Routes>

                <Route
                    path="/login"
                    element={<LoginPage />}
                />

                <Route
                    path="/"
                    element={
                        <ProtectedRoute>
                            <AdminLayout />
                        </ProtectedRoute>
                    }
                >

                    <Route
                        index
                        element={<DashboardPage />}
                    />

                    <Route
                        path="topics"
                        element={<TopicsPage />}
                    />

                    <Route
                        path="subtopics"
                        element={<SubtopicsPage />}
                    />

                    <Route
                        path="lessons"
                        element={<LessonsPage />}
                    />

                </Route>

            </Routes>

        </BrowserRouter>
    );
}

export default App;