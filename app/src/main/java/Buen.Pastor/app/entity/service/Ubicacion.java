package Buen.Pastor.app.entity.service;

public class Ubicacion {
    private int id;
    private String ambiente;
    private String ubicacionFisica;

    public Ubicacion() {
    }

    public Ubicacion(Integer id) {
        this.id = id;
    }

    public Ubicacion(Integer id, String ambiente, String ubicacionFisica) {
        this.id = id;
        this.ambiente = ambiente;
        this.ubicacionFisica = ubicacionFisica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }
}
