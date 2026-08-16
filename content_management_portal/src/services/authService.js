import api from "./api";

const authService = {

    async login(email, password) {

        const response = await api.post("/auth/admin/login", {
            email,
            password,
        });

        const token = response.data.token;

        localStorage.setItem("adminToken", token);

        return response.data;
    },

    logout() {
        localStorage.removeItem("adminToken");
    },

    isLoggedIn() {
        return localStorage.getItem("adminToken") !== null;
    },
};

export default authService;