<template>
  <div class="vui-loading">
    <div :class="className" v-if="circle > 0">
      <div :style="style"></div>
      <div :style="style"></div>
      <div :style="style"></div>
    </div>
    <div :class="className" v-if="line > 0">
  		<div :style="style"></div>
  		<div :style="style"></div>
  		<div :style="style"></div>
  		<div :style="style"></div>
  		<div :style="style"></div>
  	</div>
  </div>
</template>
<script>
export default {
  props: {
    color: {
      type: String,
      default: '#00c587'
    },
    line: {
      type: Number,
      default: 0
    },
    circle: {
      type: Number,
      default: 0
    }
  },
  data: () => ({

  }),
  computed: {
    style () {
      return {
        background: this.color
      }
    },
    className () {
      return {
        'line-scale': this.line === 1,
        'line-scale-pulse-out': this.line === 2,
        'ball-pulse-sync': this.circle === 1,
        'ball-beat': this.circle === 2,
        'ball-pulse': this.circle === 3
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.vui-loading {
  padding: 20px 0;
  text-align: center;
  .line-scale {
    & > div {
      width: 4px;
      height: 35px;
      border-radius: 2px;
      margin: 2px;
      animation-fill-mode: both;
      display: inline-block;
      &:nth-child(1) {animation: line-scale 1s 0.1s infinite cubic-bezier(.2, .68, .18, 1.08);}
      &:nth-child(2) {animation: line-scale 1s 0.2s infinite cubic-bezier(.2, .68, .18, 1.08);}
      &:nth-child(3) {animation: line-scale 1s 0.3s infinite cubic-bezier(.2, .68, .18, 1.08);}
      &:nth-child(4) {animation: line-scale 1s 0.4s infinite cubic-bezier(.2, .68, .18, 1.08);}
      &:nth-child(5) {animation: line-scale 1s 0.5s infinite cubic-bezier(.2, .68, .18, 1.08);}
    }
    @keyframes line-scale {
      0% {transform: scaley(1); }
      50% {transform: scaley(0.4); }
      100% {transform: scaley(1); }
    }
  }
  .line-scale-pulse-out {
    & > div {
      width: 4px;
      height: 35px;
      border-radius: 2px;
      margin: 2px;
      animation-fill-mode: both;
      display: inline-block;
      animation: line-scale-pulse-out 0.9s 0s infinite cubic-bezier(.85, .25, .37, .85);
      &:nth-child(2),
      &:nth-child(4) {animation-delay: 0.2s !important; }
      &:nth-child(1),
      &:nth-child(5) {animation-delay: 0.4s !important;}
    }
    @keyframes line-scale-pulse-out {
      0% {transform: scaley(1); }
      50% {transform: scaley(0.4); }
      100% {transform: scaley(1); }
    }
  }

  .ball-pulse-sync {
    & > div {
      width: 15px;
      height: 15px;
      border-radius: 100%;
      margin: 2px;
      animation-fill-mode: both;
      display: inline-block;
      &:nth-child(0) {animation: ball-pulse-sync 0.6s 0s infinite ease-in-out; }
      &:nth-child(1) {animation: ball-pulse-sync 0.6s 0.07s infinite ease-in-out; }
      &:nth-child(2) {animation: ball-pulse-sync 0.6s 0.14s infinite ease-in-out; }
      &:nth-child(3) {animation: ball-pulse-sync 0.6s 0.21s infinite ease-in-out; }
    }
    @keyframes ball-pulse-sync {
      33% {transform: translateY(10px); }
      66% {transform: translateY(-10px); }
      100% {transform: translateY(0); }
    }
  }

  .ball-beat{
    & > div {
      width: 15px;
      height: 15px;
      border-radius: 100%;
      margin: 2px;
      animation-fill-mode: both;
      display: inline-block;
      animation: ball-beat 0.7s 0s infinite linear;
      &:nth-child(2n-1) {animation-delay: 0.35s !important; }
    }
    @keyframes ball-beat {
      50% {opacity: 0.2;transform: scale(0.75); }
      100% {opacity: 1;transform: scale(1); }
    }
  }

  .ball-pulse {
    &> div {
      width: 15px;
      height: 15px;
      border-radius: 100%;
      margin: 2px;
      animation-fill-mode: both;
      display: inline-block;
      &:nth-child(0) {animation: scale 0.75s 0s infinite cubic-bezier(.2, .68, .18, 1.08); }
      &:nth-child(1) {animation: scale 0.75s 0.12s infinite cubic-bezier(.2, .68, .18, 1.08); }
      &:nth-child(2) {animation: scale 0.75s 0.24s infinite cubic-bezier(.2, .68, .18, 1.08); }
      &:nth-child(3) {animation: scale 0.75s 0.36s infinite cubic-bezier(.2, .68, .18, 1.08); }
    }
    @keyframes scale {
      0% {transform: scale(1);opacity: 1; }
      45% {transform: scale(0.1);opacity: 0.7; }
      80% {transform: scale(1);opacity: 1; }
    }
  }
}
</style>
