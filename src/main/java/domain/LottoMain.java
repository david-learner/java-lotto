package domain;

import View.ResultView;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;


public class LottoMain {
    private static LottoMachine lottoMachine = null;

    public static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    public static void main(String[] args) {
        Map<String, Object> inputStorage = new HashMap<>();
        port(8080);

        get("/", (request, response) -> {
            lottoMachine = new LottoMachine();
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });

        post("/buyLotto", (request, response) -> {
            inputStorage.put("payment", request.queryParams("inputMoney"));
            inputStorage.put("manualNumber", request.queryParams("manualNumber"));
            int payment = Integer.parseInt((String) inputStorage.get("payment"));
            int totalCount = LottoMachine.getTotalCount(payment);
            inputStorage.put("totalCount", totalCount);
            int manualCount = convertToList((String) inputStorage.get("manualNumber")).size();

            List<LottoTicket> lottoTickets = new ArrayList<>();
            lottoTickets.addAll(lottoMachine.createManualTickets(convertToList((String) inputStorage.get("manualNumber"))));
            lottoTickets.addAll(lottoMachine.createAutoTickets(LottoMachine.getAutoCount(totalCount, manualCount)));
            inputStorage.put("lottoTickets", lottoTickets);

            ResultView.printAutoManualCount(totalCount, manualCount);
            System.out.println(lottoMachine.toString());
            return render(inputStorage, "show.html");
        });
        post("/matchLotto", (request, response) -> {
            inputStorage.put("winningNumber", request.queryParams("winningNumber"));
            inputStorage.put("bonusNumber", request.queryParams("bonusNumber"));
            Result result = lottoMachine.matching((String) inputStorage.get("winningNumber"), (String) inputStorage.get("bonusNumber"), Integer.parseInt((String)inputStorage.get("payment")));
            inputStorage.put("result", result);
            return render(inputStorage, "result.html");
        });
    }

    public static List<String> convertToList(String inputManualNumber) {
        List<String> manualNumber = Arrays.asList(inputManualNumber.split("\\r?\\n"));
        System.out.println("convertToList");
        return manualNumber;
    }
}