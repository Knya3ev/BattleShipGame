package communication;

import server.state.StateGameHead;

public class CommunicationBetweenTheServerAndTheViewer {
    static class Request {
        public TranslateForServerOrView translate;
        private String HEAD = StateGameHead.START.getStateName();
        private String BODY;
        public Request(TranslateForServerOrView translate){
            this.translate = translate;

        }
        public String getHEAD() {
            return HEAD;
        }

        public void setHEAD(String HEAD) {
            this.HEAD = HEAD;
        }

        public String getBODY() {
            return BODY;
        }

        public void setBODY(String BODY) {
            this.BODY = BODY;
        }

    }

    static class Response {
        public TranslateForServerOrView translate;
        private boolean PLAYERS_IS_READY = false;
        private boolean THERE_IS_A_WINNER = false;

        public Response(TranslateForServerOrView translate){
            this.translate = translate;
        }

        public boolean isPLAYERS_IS_READY() {
            return PLAYERS_IS_READY;
        }

        public void setPLAYERS_IS_READY(boolean PLAYERS_IS_READY) {
            this.PLAYERS_IS_READY = PLAYERS_IS_READY;
        }

        public boolean isTHERE_IS_A_WINNER() {
            return THERE_IS_A_WINNER;
        }

        public void setTHERE_IS_A_WINNER(boolean THERE_IS_A_WINNER) {
            this.THERE_IS_A_WINNER = THERE_IS_A_WINNER;
        }

    }
}

