package vn.edu.fpt.authentication.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 02:27
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestDataUtils {

    public static ObjectId convertObjectId(String data){
        return data == null ? null : ObjectId.isValid(data) ? new ObjectId(data) : null;
    }

    public static String convertSearchableData(String data){
        if(Objects.nonNull(data)) {
            return "^(.)*(" + data + ")(.)*$";
        }else{
            return null;
        }
    }

    public static Boolean convertSearchableData(Boolean bool){
        if(Objects.nonNull(bool)){
            return bool;
        }else{
            return true;
        }
    }

    public static LocalDateTime convertDateFrom(String dateFrom){
        if(Objects.isNull(dateFrom) || dateFrom.isBlank()){
            return LocalDateTime.of(1900, 1, 1,0, 0, 0);
        }else{
            return DateTimeConverter.toLocalDateTime(dateFrom);
        }
    }

    public static LocalDateTime convertDateTo(String dateTo){
        if(Objects.isNull(dateTo) || dateTo.isBlank()){
            return LocalDateTime.now();
        }else{
            return DateTimeConverter.toLocalDateTime(dateTo);
        }
    }
}
