var sha1 = {
  // SHA1
  add (x, y) {
    return ((x & 0x7FFFFFFF) + (y & 0x7FFFFFFF)) ^ (x & 0x80000000) ^ (y & 0x80000000)
  },
  SHA1hex (num) {
    var sHEXChars = '0123456789abcdef'
    var str = ''
    for (var j = 7; j >= 0; j--) { str += sHEXChars.charAt((num >> (j * 4)) & 0x0F) }
    return str
  },
  AlignSHA1 (sIn) {
    var nblk = ((sIn.length + 8) >> 6) + 1
    var blks = new Array(nblk * 16)
    for (var i = 0; i < nblk * 16; i++) blks[i] = 0
    for (i = 0; i < sIn.length; i++) { blks[i >> 2] |= sIn.charCodeAt(i) << (24 - (i & 3) * 8) }
    blks[i >> 2] |= 0x80 << (24 - (i & 3) * 8)
    blks[nblk * 16 - 1] = sIn.length * 8
    return blks
  },
  rol (num, cnt) {
    return (num << cnt) | (num >>> (32 - cnt))
  },
  ft (t, b, c, d) {
    if (t < 20) return (b & c) | ((~b) & d)
    if (t < 40) return b ^ c ^ d
    if (t < 60) return (b & c) | (b & d) | (c & d)
    return b ^ c ^ d
  },
  kt (t) {
    return (t < 20) ? 1518500249 : (t < 40) ? 1859775393
      : (t < 60) ? -1894007588 : -899497514
  },
  SHA1 (sIn) {
    var x = this.AlignSHA1(sIn)
    var w = new Array(80)
    var a = 1732584193
    var b = -271733879
    var c = -1732584194
    var d = 271733878
    var e = -1009589776
    for (var i = 0; i < x.length; i += 16) {
      var olda = a
      var oldb = b
      var oldc = c
      var oldd = d
      var olde = e
      for (var j = 0; j < 80; j++) {
        if (j < 16) w[j] = x[i + j]
        else w[j] = this.rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1)
        var t = this.add(this.add(this.rol(a, 5), this.ft(j, b, c, d)), this.add(this.add(e, w[j]), this.kt(j)))
        e = d
        d = c
        c = this.rol(b, 30)
        b = a
        a = t
      }
      a = this.add(a, olda)
      b = this.add(b, oldb)
      c = this.add(c, oldc)
      d = this.add(d, oldd)
      e = this.add(e, olde)
    }
    var SHA1Value = this.SHA1hex(a) + this.SHA1hex(b) + this.SHA1hex(c) + this.SHA1hex(d) + this.SHA1hex(e)
    return SHA1Value.toUpperCase()
  },
  SHA2 (sIn) {
    return this.SHA1(sIn).toLowerCase()
  }
}

module.exports = sha1
