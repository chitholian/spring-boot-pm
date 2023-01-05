export function extractErr(e) {
    let obj = {
        error: '',
        errors: {},
    }
    if (e.response.status) {
        let d = e.response.data
        if (d) {
            if (d.data) {
                if (d.data.error) {
                    obj.error = d.data.error
                } else if (d.error) {
                    obj.error = d.error
                }
                if (d.data.errors) {
                    obj.errors = d.data.errors
                }
            } else if (d.error) {
                obj.error = d.error
            }
        } else {
            obj.error = e.response.statusText
        }
    } else {
        obj.error = e.message
    }
    return [obj.error, obj.errors];
}
