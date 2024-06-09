package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.entity.service.TeacherPayment;
import Buen.Pastor.app.repository.PagoRepository;
import okhttp3.ResponseBody;

import java.util.List;

public class PagoViewModel extends AndroidViewModel {

    private PagoRepository pagoRepository;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        pagoRepository = new PagoRepository();
    }

    // Método para agregar un pago
    public LiveData<BestGenericResponse<TeacherPayment>> agregarPago(TeacherPayment pago) {
        return pagoRepository.agregarPago(pago);
    }

    // Método para editar un pago
    public LiveData<BestGenericResponse<TeacherPayment>> editarPago(TeacherPayment pago) {
        return pagoRepository.editarPago(pago);
    }

    // Método para eliminar un pago
    public LiveData<BestGenericResponse<Void>> eliminarPago(int id) {
        return pagoRepository.eliminarPago(id);
    }

    // Método para listar todos los pagos
    public LiveData<BestGenericResponse<List<TeacherPaymentDTO>>> listarTodosLosPagos() {
        return pagoRepository.listarTodosLosPagos();
    }
    public LiveData<BestGenericResponse<TeacherPaymentDTO>> obtenerPagoPorId(int id) {
        return pagoRepository.obtenerPagoPorId(id);
    }
    // Método para generar un baucher en PDF de un pago específico
    public LiveData<ResponseBody> generarBaucherPdf(int paymentId) {
        return pagoRepository.generarBaucherPdf(paymentId);
    }

    // Método para descargar un reporte de todos los pagos en formato Excel
    public LiveData<ResponseBody> generateExcelReport() {
        return pagoRepository.generateExcelReport();
    }

    // Método para obtener la lista de pagos de un docente específico
    public LiveData<BestGenericResponse<List<TeacherPaymentDTO>>> listarPagosPorDocenteId(int teacherId) {
        return pagoRepository.listarPagosPorDocenteId(teacherId);
    }
}
