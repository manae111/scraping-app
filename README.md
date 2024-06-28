<div id="top"></div>

## 使用技術一覧

<!-- シールド一覧 -->
<!-- 該当するプロジェクトの中から任意のものを選ぶ-->
<p style="display: inline">
  <!-- フロントエンドのフレームワーク一覧 -->
  <img src="https://img.shields.io/badge/-Node.js-000000.svg?logo=node.js&style=for-the-badge">
  <!-- フロントエンドの言語一覧 -->
  <img src="https://img.shields.io/badge/-JAVASCRIPT-000000.svg?logo=javascript&style=for-the-badge">
  <!-- バックエンドのフレームワーク一覧 -->
  <img src="https://img.shields.io/badge/-SPRINGBOOT-000000.svg?logo=springboot&style=for-the-badge">
  <img src="https://img.shields.io/badge/-SPRINFSECURITY-000000.svg?logo=springsecurity&style=for-the-badge">
  <!-- バックエンドの言語一覧 -->
  <img src="https://img.shields.io/badge/-JAVA-000000.svg?logo=java&style=for-the-badge">
  <!-- ミドルウェア一覧 -->
  <img src="https://img.shields.io/badge/-POSTGRESQL-4479A1.svg?logo=postgresql&style=for-the-badge&logoColor=white">
  <!-- インフラ一覧 -->
  <img src="https://img.shields.io/badge/-Docker-1488C6.svg?logo=docker&style=for-the-badge">
</p>

## 目次

1. [プロジェクトについて](#プロジェクトについて)
2. [環境](#環境)
3. [構成](#構成)
4. [開発環境構築](#開発環境構築)
5. [機能一覧](#機能一覧)
6. [トラブルシューティング](#トラブルシューティング)

<!-- プロジェクト名を記載 -->

## プロジェクト名

Webスクレイピングアプリケーション

<!-- プロジェクトについて -->

## プロジェクトについて

<!-- プロジェクトの概要を記載 -->
Amazonの商品ページのURLを入力すると値段等がスクレイピングされDBに登録されます。  
毎日11:00のバッチ処理時に登録してある商品の最新の値段をDBに保存、登録時の値段と比較し、  
登録時よりも安くなっていた場合、メールピットにその商品の情報を送信します。  
ユーザーはサービス利用時にメールアドレスとパスワードを登録する必要があり、登録商品はユーザーごとに管理されます。

<p align="right">(<a href="#top">トップへ</a>)</p>

## 環境

<!-- 言語、フレームワーク、ミドルウェア、インフラの一覧とバージョンを記載 -->

| 言語・フレームワーク  | バージョン |
| --------------------- | ---------- |
| Node.js               | 20.14.0    |
| SpringBoot            | 3.3.0      |
| SpringSecurity        | 6.3.0      |
| java                  | 17.0.10    |
| PostgreSQL            | 11.22      |
| Docker                | 25.0.3     |

その他のパッケージのバージョンは package.json を参照してください

<p align="right">(<a href="#top">トップへ</a>)</p>

## 構成

### ディレクトリ

<!-- Treeコマンドを使ってディレクトリ構成を記載 -->

❯ tree -a -I "node_modules|.next|.git|.pytest_cache|static" -L 3
<pre>
.
├── .vscode
│   ├── launch.json
│   └── settings.json
├── demo
│   ├── .gitignore
│   ├── .gradle
│   │   ├── 8.7
│   │   ├── buildOutputCleanup
│   │   └── vcs-1
│   ├── .vscode
│   │   └── NEWLY_CREATED_BY_SPRING_INITIALIZR
│   ├── HELP.md
│   ├── bin
│   │   ├── main
│   │   └── test
│   ├── build.gradle
│   ├── gradle
│   │   └── wrapper
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   ├── sql
│   │   └── ddl_scraping.txt
│   └── src
│       ├── main
│       └── test
├── package-lock.json
└── package.json
</pre>

### ER図
```mermaid
---
title: scraping-app
---
erDiagram


    items {
        int id PK
        string url
        string item_name
        int price_original
        int price_latest
        int user_id FK
    }

    users {
        int id PK
        string username
        string password
    }

    users ||--o{ items :has

```

### 画面遷移図
<img src="img/screen-transition.png" width="500">

### シーケンス図
```mermaid

sequenceDiagram
    participant User
    participant Application
    participant Database

    User ->> Application: ログイン
    Application -->> User: ログイン認証

    User ->> Application: URL入力
    Application ->> Database: 商品登録
    Application ->> Database: バッチ処理
    Database -->> Application: 比較結果返却
  　Application -->> User: メール送信

```

<p align="right">(<a href="#top">トップへ</a>)</p>

## 開発環境構築

<!-- コンテナの作成方法、パッケージのインストール方法など、開発環境構築に必要な情報を記載 -->

### 作成と起動

DBとテーブルを作成する  
node server.jsでサーバーを起動する  
DemoApplicationを起動する  
dockerでmailpitを起動する

### 動作確認

http://localhost:3000 （サーバー）  
http://localhost:8025 （mailpit）  
http://localhost:8080/toLogin にアクセスできるか確認。

### 環境変数の一覧

| 変数名                 | 役割                                      | デフォルト値                       |
| ---------------------- | ----------------------------------------- | ---------------------------------- |
| MAIL_HOST              | mailpit のホスト名（Docker で使用）        | localhost                           |
| MAIL_PORT              | mailpit のポート番号（Docker で使用）      | 1025                                |
| DB_USER                | PostgreSQL のユーザ名                     | postgres                            |
| DB_PASS                | PostgreSQL のパスワード                   | postgres                            |
| DB_HOST                | PostgreSQL のホスト名                     | localhost                           |
| DB_PORT                | PostgreSQL のポート番号                   | 5432                                |
| DB＿NAME               | PostgreSQL のデータベース名                | scraping                            |

<p align="right">(<a href="#top">トップへ</a>)</p>

## 機能一覧
### ログイン画面
<img src="img/login.png" width="500">

メールアドレスとパスワードで、SpringSecurityを使用したログイン認証を行います。  
ログインが失敗した場合はエラーメッセージが表示されます。
  
### 新規登録画面
<img src="img/register.png" width="500">

メールアドレスは重複不可です。 
不備がある場合はエラーメッセージが表示されます。

### URL入力画面
<img src="img/scrape.png" width="500">

上部にURLを入力するテキストボックス、下部に登録した商品の一覧が表示されます。  
Scrapeボタンを押すとボタンの表記が解析中に変わり、解析中の二重サブミットを防ぎます。  
終了後は結果によって、テキストボックスの下に成功・失敗の文字が表示されます。  
商品の削除やログアウトもこのページで行います。

<p align="right">(<a href="#top">トップへ</a>)</p>

## トラブルシューティング

### ・サーバーの作成ができない
jsフォルダまで移動してからコマンドを入力してください。

### ・URL入力後、「解析が失敗しました」と表示される
取得元のデータの構造により、解析ができない商品があります。他の商品を登録してください。

### ・最新価格に「データなし」と表示される
取得元のデータの変化により、最新価格の取得ができなくなる場合があります。削除して新しく登録し直してください。  
また、商品情報を取得した直後から初回のバッチ処理までは全てデータなしと表示されます。


<p align="right">(<a href="#top">トップへ</a>)</p>
