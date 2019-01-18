package ch.bbw.zork;
// TODO: Command say einbauen und darauf antworten || Player töten wenn dieser zulange im Raum bleibt. || Ghosts erstellen im Raum.
public class Ghost {


    private boolean friendly;

    public Ghost(boolean friendly){
        this.friendly = friendly;
    }

    public String responseToPlyer(String playerQuestion){
        String response = "Sorry, I can't help you.";
        if(playerQuestion.contains("help")){
            //TODO: Add response with some logic
            response = "For your future protection against angry ghosts: There is a magical coat in the garden...";
        }
        return response;
    }

    public boolean killPlayer(boolean hasMagicCoat){
        if (hasMagicCoat) {
            return false;
        }
        return true;
    }

    public boolean isFriendly(){
        return friendly;
    }

}
