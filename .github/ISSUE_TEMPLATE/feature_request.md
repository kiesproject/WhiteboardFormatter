---
name: Feature request
about: Suggest an idea for this project
title: ''
labels: ''
assignees: ''

---

## 概要
Buttonが押されたときにAPIにリクエストを送信する。

## 目的
APIから、天気情報を取得するため。

## タスク
1. buttonにOnClick属性を追加する。
2. APIにリクエストを送信する。

## 実装方法
1. setOnClickListenerを用いてbuttonにonClick属性を追加する。
2. HTTPConnect クラスを用いて、通信を行う。

### 備考
HTTPConnect使うときは、プロキシにの設定に気を付けてね。
