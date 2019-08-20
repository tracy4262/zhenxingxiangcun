(function() {

    if (!sessionStorage.length) {
        // 这个调用能触发目标事件，从而达到共享数据的目的
        localStorage.setItem('getSessionStorage', Date.now())
    }

    // 该事件是核心
    window.addEventListener('storage', function(event) {
        if (event.key == 'getSessionStorage') {
            // 已存在的标签页会收到这个事件
            localStorage.setItem('sessionStorage', JSON.stringify(sessionStorage))
            localStorage.removeItem('sessionStorage')

        } else if (event.key == 'sessionStorage' && !sessionStorage.length) {
            // 新开启的标签页会收到这个事件
            var data = JSON.parse(event.newValue),
                    value

            for (key in data) {
                sessionStorage.setItem(key, data[key])
            }
        }
    })
})()