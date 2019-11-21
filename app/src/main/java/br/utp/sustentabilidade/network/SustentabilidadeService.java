package br.utp.sustentabilidade.network;

import java.util.List;

import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SustentabilidadeService {

    @GET("organico/{id}")
    Call<RespostaJSON<Organico>> listarOrganico(@Path("id") int id);

    @GET("organico/all/{pagina}")
    Call<RespostaJSON<List<Organico>>> listarOrganicos(@Path("pagina") int pagina);

    @POST("organico/new")
    Call<RespostaJSON<Organico>> inserirOrganico(@Body Organico organico);

    @PUT("organico")
    Call<RespostaJSON<Organico>> atualizarOrganico(@Body Organico organico);

    @GET("organico/{id}")
    Call<RespostaJSON<Organico>> removerOrganico(@Path("id") int id);


    @GET("reciclagem/{id}")
    Call<RespostaJSON<Reciclagem>> listarReciclagem(@Path("id") int id);

    @GET("reciclagem/all/{pagina}")
    Call<RespostaJSON<List<Reciclagem>>> listarReciclagems(@Path("pagina") int pagina);

    @POST("reciclagem/new")
    Call<RespostaJSON<Reciclagem>> inserirReciclagem(@Body Reciclagem reciclagem);

    @PUT("reciclagem")
    Call<RespostaJSON<Reciclagem>> atualizarReciclagem(@Body Reciclagem reciclagem);

    @GET("reciclagem/{id}")
    Call<RespostaJSON<Reciclagem>> removerReciclagem(@Path("id") String id);


    @GET("agrotoxico/{id}")
    Call<RespostaJSON<Agrotoxico>> listarAgrotoxico(@Path("id") int id);

    @GET("agrotoxico/all/{pagina}")
    Call<RespostaJSON<List<Agrotoxico>>> listarAgrotoxicos(@Path("pagina") int pagina);

    @POST("agrotoxico/new")
    Call<RespostaJSON<Agrotoxico>> inserirAgrotoxico(@Body Agrotoxico agrotoxico);

    @PUT("agrotoxico")
    Call<RespostaJSON<Agrotoxico>> atualizarAgrotoxico(@Body Agrotoxico agrotoxico);

    @GET("agrotoxico/{id}")
    Call<RespostaJSON<Agrotoxico>> removerAgrotoxico(@Path("id") int id);

}
