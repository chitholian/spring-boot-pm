import axios from "axios";
import Config from "$lib/Config.js";

export default {
    openProjectListReport() {
        axios.get(Config.apiBase + '/reports/project-list', {
            responseType: "blob"
        }).then(({data}) => {
            window.open(URL.createObjectURL(data))
        }).catch(err => {
            alert(err.message)
        })
    }
}
