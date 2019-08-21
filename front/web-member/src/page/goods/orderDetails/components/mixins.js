export const timeFormat = {
  data() {
  },
  methods: {
    getNowTime (time) {
        const year = time.getFullYear();
        const month = time.getMonth();
        const day = time.getDate();
        const hour = time.getHours();
        const minute = time.getMinutes();
        return [year,month,day,hour,minute];
    },
    timeFormat (oldtime, number) {
        let oldtimes = this.getNowTime(new Date(oldtime));  // 获取到旧时间
        let nowtimes = this.getNowTime(new Date()); // 获取新的时间
        let subYear = this.moment(nowtimes).diff(oldtimes, 'year')
        let submonth = this.moment(nowtimes).diff(oldtimes, 'month')
        submonth = submonth % 12
        let subdays = this.moment(nowtimes).diff(oldtimes, 'days')
        // subdays = subdays - (submonth * 30)
        let subhour = this.moment(nowtimes).diff(oldtimes, 'hour')
        subhour = subhour - (subdays * 24)
        let subminute = this.moment(nowtimes).diff(oldtimes, 'minute')
        subminute = subminute - (subhour * 60)
        if (number) {
            return subminute
        } else {
            if (subYear >= 1) {
                return `${subYear}年${submonth}月`
            } else if (submonth >= 1) {
                return `${submonth}月`                 
            } else if (subdays >= 1) {
                return `${subdays}天${subhour}小时`
            } else if (subhour >= 1) {
                return `${subhour}小时${subminute}分`
            } else if (subminute >= 1) {
                return `${subminute}分钟`
            } else {
                return `${subminute}分钟`
            }
        }
    }
  }
}