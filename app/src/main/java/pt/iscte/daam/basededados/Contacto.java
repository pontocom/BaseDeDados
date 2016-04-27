package pt.iscte.daam.basededados;

/**
 * Created by cserrao on 15/03/16.
 */
public class Contacto {

    protected long id;
    protected String nome, email, telephone;

    public Contacto() {
    }

    public Contacto(long id, String nome, String email, String telephone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telephone = telephone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
