package jmu.controller;

import jmu.service.SignAndLoginService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/signAndLogin")
public class SignAndLoginController {
    public static int USERSID;

    @Autowired
    private SignAndLoginService signAndLoginService;

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String sign(@RequestParam("account") String account,
                       @RequestParam("password") String password,
                       @RequestParam("identity") String identity,
                       @RequestParam("question") String question,
                       @RequestParam("answer") String answer,
                       Model model) {
        String path = null;
        if(account.isEmpty()||password.isEmpty()||identity.isEmpty()){
            return "signup";
        }
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setIdentity(identity);
        user.setSecurityQuestion(question);
        user.setSecurityAnswer(answer);
        try{
            boolean flag = signAndLoginService.sign(user);
            if(flag == true){
                path = "redirect:/login.html";
                model.addAttribute("userAccount",account);
                model.addAttribute("errorMessage",null);
            }else {
                path = "redirect:/signupResult.html";
                model.addAttribute("errorMessage", "注册失败，请重试");
            }
        } catch (DuplicateKeyException e) {
            path = "redirect:/signupResult.html";
            model.addAttribute("errorMessage", "该账号已经存在，请选择其他账号");
        } catch (Exception e) {
            path = "redirect:/signupResult.html";
            model.addAttribute("errorMessage","注册失败，请重试");
        }
        return path;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            Model model) {

        String path = null;
        if(account.isEmpty() || password.isEmpty()){
            model.addAttribute("errorMessage", "账号或密码为空");
            return "redirect:/login.html";
        }
        try{
            User user = signAndLoginService.login(account, password);
            if(user == null){
                model.addAttribute("errorMessage", "账号或密码错误");
                path = "loginResult.html";
            }else{
                this.USERSID = user.getUserID();
                if(user.getIdentity().equals("buyer")){
                    path = "redirect:/buyerPage-home.html";
                } else if (user.getIdentity().equals("seller")) {
                    path = "sellerPage";
                }
            }
        }catch (Exception e){
            path = "redirect:/loginResult.html";
        }
        return path;
    }

    @RequestMapping(value = "/loginout", method = RequestMethod.POST)
    public String login(){
        this.USERSID = 0;
        return "redirect:/login.html";
    }

    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public String findPassword(
            @RequestParam("account") String account,
            @RequestParam("securityQuestion") String securityQuestion,
            @RequestParam("securityAnswer") String securityAnswer,
            Model model){
        String path;
        User user = signAndLoginService.findPassword(account);
        if(user == null){
            model.addAttribute("errorMessage", "您的输入有误,不能为您找回密码");
            path = "";
        }else{
            if(securityQuestion.equals(user.getSecurityQuestion()) &&
               securityAnswer.equals(user.getSecurityAnswer())){
                path = "";
            }else{
                model.addAttribute("errorMessage", "您的输入有误,不能为您找回密码");
                path = "";
            }
        }
        return path;
    }
}
