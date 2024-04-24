package com.example.chessboard;


    public class Uzytkownik {

        private String login="";
        private String haslo="";
        private String pseudonim="";
        public  int ranking;
        public int wygrane;
        public int przegrane;
        public int remisy;
        public int id;
        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getHaslo() {
            return haslo;
        }

        public void setHaslo(String haslo) {
            this.haslo = haslo;
        }

        public String getPseudonim() {
            return pseudonim;
        }

        public void setPseudonim(String pseudonim) {
            this.pseudonim = pseudonim;
        }
    }


