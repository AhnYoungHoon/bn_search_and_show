package com.example.NTSBusinessNum.User;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Validated UserCreateForm userCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2()))
        {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개 의 패 스 워 드 가 일 치 하 지 않 습 니 다.");
            return "signup_form";
        }
        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이 미 등 록 된 사 용 자 입 니 다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_form";
    }

//    @PostMapping("/login")
//    public String login(Model model, UserLoginForm userLoginForm){
//        System.out.println("aaa");
//        String username = userLoginForm.getUsername();
//        Long userId = userService.findUserId(username);
//
//        model.addAttribute("userId", userId);
//        return "/";
//    }

    @GetMapping("/mypage")
    public String mypage(){

        return "mypage";
    }

    @GetMapping("/mypage/reportBusiness")
    public String reportBusiness(){
        return "reportBusiness";
    }

    @PostMapping("/mypage/reportBusiness")
    public String reportBusinessAction(@Validated MypageSiteReportForm mypageSiteReportForm, Model model){
        String username = mypageSiteReportForm.getUsername();
        String url = mypageSiteReportForm.getUrl();
        String businessNum = mypageSiteReportForm.getBusinessNum();
        userService.report(username, url, businessNum);

        return "/mypage";
    }

    @GetMapping("/mypage/informDamage")
    public String informDamage(){
        return "informDamage";
    }
}
