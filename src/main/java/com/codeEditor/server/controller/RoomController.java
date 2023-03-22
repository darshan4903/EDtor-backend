package com.codeEditor.server.controller;

import com.amazonaws.http.JsonErrorResponseHandler;
import com.codeEditor.server.entity.ResponseDTO;
import com.codeEditor.server.entity.Room;
import com.codeEditor.server.service.RoomService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseDTO> getRoom(@PathVariable String id){
        try {
            Room room = roomService.find(id);
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .success(Boolean.TRUE)
                            .message("Room Fetched")
                            .data(room)
                            .build(),
                    HttpStatus.OK
            );
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .success(Boolean.FALSE)
                            .message(e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping@CrossOrigin
    public ResponseEntity<ResponseDTO> createRoom() {
        try {
            Room room = roomService.createRoom();
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .message("Room Created")
                    .data(room)
                    .success(Boolean.TRUE)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .message(e.getMessage())
                    .data(null)
                    .success(Boolean.FALSE)
                    .build();
            return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @GetMapping("/res/{id}/{stdin}")
    public JSONObject compileRes(@PathVariable String id , @PathVariable String stdin) throws Exception {
//        try {
            Room room = roomService.find(id);
            String str = room.getCode();
            String encoded = roomService.getEncodedCode(str);
            String urlpost = "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true&fields=*";

            String temp = "{\"language_id\": 52,\"source_code\": \""+encoded+"\",\"stdin\": \""+stdin+"\"}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key","0fbf3a83d9mshb98fa5625fbee9ep140cccjsn2170a61c54d9");
            headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(temp,headers);
            ResponseEntity<JSONObject> res = restTemplate.exchange(urlpost,HttpMethod.POST,entity, JSONObject.class);
            System.out.println(res);

            JSONObject token_val = res.getBody();
            String token = token_val.get("token").toString();

            String urlget = "https://judge0-ce.p.rapidapi.com/submissions/"+token;
            ResponseEntity<JSONObject> ans = restTemplate.exchange(urlget,HttpMethod.GET,entity, JSONObject.class);
            JSONObject ans_output = ans.getBody();
//            String output = ans_output.get("stdout").toString();
//            System.out.println(output);
            return ans_output;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return ;
//        }

    }

}
