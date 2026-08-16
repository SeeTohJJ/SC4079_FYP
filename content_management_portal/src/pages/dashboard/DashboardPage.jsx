function DashboardPage() {

    return (
        <div>

            <h1>Dashboard</h1>

            <div className="dashboard-grid">

                <div className="dashboard-card">
                    <h2>Topics</h2>
                    <p>Manage financial literacy topics.</p>
                </div>

                <div className="dashboard-card">
                    <h2>Subtopics</h2>
                    <p>Manage topic subtopics.</p>
                </div>

                <div className="dashboard-card">
                    <h2>Lessons</h2>
                    <p>Create and edit learning content.</p>
                </div>

                <div className="dashboard-card">
                    <h2>Quizzes</h2>
                    <p>Manage quiz questions and answers.</p>
                </div>

                <div className="dashboard-card">
                    <h2>Study Chains</h2>
                    <p>Manage study chain templates.</p>
                </div>
            </div>

        </div>
    );
}

export default DashboardPage;