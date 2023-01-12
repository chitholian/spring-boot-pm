import axios from "axios";
import Config from "$lib/Config.js";
import {goto} from "$app/navigation";
import {user, jwt} from "$lib/stores/user.store.js";

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
    },
    logout() {
        localStorage.removeItem('jt-pm-jwt')
        window.location.href = '/login'
    },
    storeAuthInfo(userInfo, token) {
        localStorage.setItem('jt-pm-jwt', token)
        jwt.set(token)
        user.set(userInfo)
    },
    initialize() {
        // 1. If we have JWT try it.
        // 2. If OK, set user and go home.
        // 3. Else go to login page.

        let wt = localStorage.getItem('jt-pm-jwt')
        if (wt) {
            // try it.
            axios.get(Config.apiBase + '/who-am-i', {
                headers: {
                    Authorization: 'Bearer ' + wt,
                },
            }).then(({data}) => {
                if (data.status === 200) {
                    this.storeAuthInfo(data.data.user, wt)
                }
            }).catch(err => {
                if (err.response && err.response.status === 401) {
                    goto('/login', {replaceState: true})
                } else {
                    alert(err.message)
                }
            })
        } else {
            goto('/login', {replaceState: true})
        }
    },
}
