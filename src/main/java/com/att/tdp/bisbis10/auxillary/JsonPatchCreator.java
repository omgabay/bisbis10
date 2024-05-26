package com.att.tdp.bisbis10.auxillary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.diff.JsonDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonPatchCreator {


        @Autowired
        private ObjectMapper objectMapper;


        public JsonPatchCreator(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public JsonPatch createPatch(Object originalObject, Object modifiedObject) throws JsonPatchException {
            // Convert objects to JsonNode
            JsonNode originalNode = objectMapper.convertValue(originalObject, JsonNode.class);
            JsonNode modifiedNode = objectMapper.convertValue(modifiedObject, JsonNode.class);
            mergeMissingFields(modifiedNode, originalNode);
            System.out.println("Original node: " + originalNode);
            System.out.println("Modified node: " + modifiedNode);
            // Create a JsonPatch
            return JsonDiff.asJsonPatch(originalNode, modifiedNode);
        }


    public void mergeMissingFields(JsonNode target, JsonNode source) {
        if (source.isEmpty()) {
            return;
        }

        for (JsonNode field : source) {
            String fieldName = field.fieldNames().next();
            // Check if field already exists in target
            if (!target.has(fieldName)) {
                ((ObjectNode) target).set(fieldName, field);
            } else {
                // If both are objects, merge recursively
                if (field.isObject() && target.get(fieldName).isObject()) {
                    mergeMissingFields(target.get(fieldName), field);
                }
            }
        }
    }


    public <T> T applyPatch(JsonPatch patch, T targetObject, Class<T> targetClass) throws JsonPatchException, JsonProcessingException {
        // Convert the target object to JsonNode
        JsonNode targetNode = objectMapper.convertValue(targetObject, JsonNode.class);

        // Apply the patch
        JsonNode patchedNode = patch.apply(targetNode);
        System.out.println("Patched node: " + patchedNode);

        // Convert the patched node back to the target object
        return objectMapper.treeToValue(patchedNode, targetClass);
    }


}




