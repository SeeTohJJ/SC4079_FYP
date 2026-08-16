function Navbar({ title }) {

    return (
        <header className="navbar">

            <h1>{title}</h1>

            <div className="admin-info">
                Administrator
            </div>

        </header>
    );
}

export default Navbar;