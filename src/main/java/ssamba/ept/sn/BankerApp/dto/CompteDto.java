package ssamba.ept.sn.BankerApp.dto;


import lombok.*;
@Getter
@Setter
public class CompteDto {
    private double solde;
    private double decouvert;
    private int clientId ;
    private int agenceCode ;
}
