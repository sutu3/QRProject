package org.example.qrproject.Service;

import org.example.qrproject.Dtos.Request.Attribute.AttributeRequest;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Enum.DataType;
import org.example.qrproject.Module.AttributeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttributeService {
    AttributeResponse create(AttributeRequest req);
    AttributeEntity getOwned(String id);
    List<AttributeResponse> list();
    void delete(String id);
}
