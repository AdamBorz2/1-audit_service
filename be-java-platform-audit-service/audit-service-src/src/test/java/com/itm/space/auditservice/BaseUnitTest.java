package com.itm.space.auditservice;

import com.itm.space.auditservice.util.JsonParserUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

    protected static JsonParserUtil jsonParserUtil = new JsonParserUtil();
}