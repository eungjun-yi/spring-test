package hello;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class HelloService {
    // getValueInternal():75, VariableReference (org.springframework.expression.spel.ast) 에서 #targe를 찾아보고 null 반환
    @PreAuthorize("hasPermission(#targe, 'admin')")
    public String admin(String target) {
        return "ok";
    }
}