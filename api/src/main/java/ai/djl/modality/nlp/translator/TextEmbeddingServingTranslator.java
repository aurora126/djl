/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.modality.nlp.translator;

import ai.djl.modality.Input;
import ai.djl.modality.Output;
import ai.djl.ndarray.NDList;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.util.JsonUtils;

/** A {@link Translator} that can handle generic text embedding {@link Input} and {@link Output}. */
public class TextEmbeddingServingTranslator implements Translator<Input, Output> {

    private Translator<String, float[]> translator;

    /**
     * Constructs a {@code TextEmbeddingServingTranslator} instance.
     *
     * @param translator a {@code Translator} processes text embedding input
     */
    public TextEmbeddingServingTranslator(Translator<String, float[]> translator) {
        this.translator = translator;
    }

    /** {@inheritDoc} */
    @Override
    public Batchifier getBatchifier() {
        return translator.getBatchifier();
    }

    /** {@inheritDoc} */
    @Override
    public void prepare(TranslatorContext ctx) throws Exception {
        translator.prepare(ctx);
    }

    /** {@inheritDoc} */
    @Override
    public NDList processInput(TranslatorContext ctx, Input input) throws Exception {
        String text = input.getData().getAsString();
        return translator.processInput(ctx, text);
    }

    /** {@inheritDoc} */
    @Override
    public Output processOutput(TranslatorContext ctx, NDList list) throws Exception {
        float[] ret = translator.processOutput(ctx, list);
        Output output = new Output();
        output.add(JsonUtils.GSON_PRETTY.toJson(ret));
        return output;
    }
}
