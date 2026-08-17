import { useEffect, useState } from "react";
import dashboardService from "../../services/dashboardService";
import "./DashboardPage.css";

function DashboardPage() {

    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadDashboard = async () => {

        try {
            setLoading(true);
            setError("");

            const data = await dashboardService.getDashboardData();

            setDashboard(data);
        } catch (error) {

            console.error("Failed to load dashboard:", error);

            setError(error.response?.data?.message || "Failed to load dashboard data.");

        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadDashboard();
    }, []);

    if (loading) {
        return (
            <div className="main-content">

                <h1>Dashboard</h1>

                <p>
                    Loading dashboard...
                </p>

            </div>
        );
    }

    if (error) {

        return (
            <div className="main-content">

                <h1>Dashboard</h1>

                <div className="error-message">
                    {error}
                </div>

            </div>
        );
    }

    return (

        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>
                        Dashboard
                    </h1>
                </div>
            </div>


            <div className="dashboard-grid">
                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Users
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.userCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Admins
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.adminCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Number of Topics
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.topicCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Number of Subtopics
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.subtopicCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Number of Lesson Nodes
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.lessonCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Number of Quiz Nodes
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.quizCount}
                    </div>
                </div>

                <div className="dashboard-card">
                    <div className="dashboard-card-title">
                        Total Number of Study Chains
                    </div>

                    <div className="dashboard-card-value">
                        {dashboard.studyChainCount}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DashboardPage;