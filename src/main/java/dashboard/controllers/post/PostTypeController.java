package dashboard.controllers.post;

import dashboard.commons.ValidationUtils;
import dashboard.dto.post.PostTypeForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post/type")
public class PostTypeController {

    @Autowired
    PostTypeService postTypeService;

    @GetMapping
    public ResponseEntity index () {
        return ResponseEntity.ok(postTypeService.getAll());
    }

    @GetMapping("{postTypeId}")
    public ResponseEntity getOne(
        @PathVariable(name = "postTypeId") Long postTypeId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(postTypeService.getOne(postTypeId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody PostTypeForm postTypeForm) {
        ValidationUtils.validate(postTypeForm);
        postTypeService.create(postTypeForm);
        return ResponseEntity.ok(postTypeService.getAll());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody PostTypeForm postTypeForm
    ) throws ResourceNotFoundException{
        ValidationUtils.validate(postTypeForm);
        postTypeService.update(postTypeForm);
        return ResponseEntity.ok(postTypeService.getAll());
    }

    @DeleteMapping(value = "{postTypeId}")
    public ResponseEntity delete(@PathVariable(name = "postTypeId") Long postTypeId) {
        postTypeService.delete(postTypeId);
        return ResponseEntity.ok(postTypeService.getAll());
    }
}