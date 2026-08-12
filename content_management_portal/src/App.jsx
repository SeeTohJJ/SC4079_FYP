import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
// import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminLayout from "./layouts/AdminLayout";

function App() {
    return (
        <BrowserRouter>
            <Routes>

                <Route
                    path="/login"
                    element={<LoginPage />}
                />

                <Route
                    element={
                        <ProtectedRoute>
                            <AdminLayout />
                        </ProtectedRoute>
                    }
                >
                    <Route
                        path="/dashboard"
                        element={<DashboardPage />}
                    />

                    <Route
                        path="/topics"
                        element={<div>Topics</div>}
                    />

                    <Route
                        path="/subtopics"
                        element={<div>Subtopics</div>}
                    />

                    <Route
                        path="/lessons"
                        element={<div>Lessons</div>}
                    />

                    <Route
                        path="/quizzes"
                        element={<div>Quizzes</div>}
                    />
                </Route>

            </Routes>
        </BrowserRouter>
    );
}

export default App;