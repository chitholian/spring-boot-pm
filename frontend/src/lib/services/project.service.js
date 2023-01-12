import axios from "axios";
import Config from "$lib/Config.js";

export default {
    fetchProjects(page = 1, perPage = 20) {
        return axios.get(Config.apiBase + '/projects', {
            data: {
                page,
                perPage,
            },
        })
    },
    fetchProject(id) {
        return axios.get(Config.apiBase + '/projects/' + id)
    },
    startProject(id) {
        return axios.patch(Config.apiBase + '/projects/' + id + '/start')
    },
    endProject(id) {
        return axios.patch(Config.apiBase + '/projects/' + id + '/end')
    },
    fetchMembers(id) {
        return axios.get(Config.apiBase + '/projects/' + id + '/members')
    },
    fetchItem(id) {
        return axios.get(Config.apiBase + '/projects/' + id)
    },
    removeMembers(id, userIds) {
        return axios.delete(Config.apiBase + '/projects/' + id + '/remove-members', {
            data: {userIds,}
        })
    },
    addMembers(id, emails) {
        return axios.patch(Config.apiBase + '/projects/' + id + '/add-members', {
            userEmails: emails,
        })
    },
    createProject(name, intro, description, startNow) {
        return axios.post(Config.apiBase + '/projects', {
            name, intro, description, startNow,
        })
    },
    deleteProject(id) {
        return axios.delete(Config.apiBase + '/projects/' + id)
    },
    editProject(id, name, intro, description) {
        return axios.patch(Config.apiBase + '/projects/' + id, {
            name, intro, description,
        })
    }
}
