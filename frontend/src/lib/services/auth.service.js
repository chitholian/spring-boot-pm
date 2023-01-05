import axios from "axios";
import Config from "$lib/Config.js";

export default {
    login(username, password) {
        return axios.post(Config.apiBase + '/login', {
            username, password
        })
    },
    register(name, email, password) {
        return axios.post(Config.apiBase + '/register', {
            name, email, password
        })
    }
}
