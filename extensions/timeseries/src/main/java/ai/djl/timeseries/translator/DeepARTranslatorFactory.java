/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
package ai.djl.timeseries.translator;

import ai.djl.Model;
import ai.djl.timeseries.Forecast;
import ai.djl.timeseries.TimeSeriesData;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorFactory;
import ai.djl.util.Pair;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/** A {@link TranslatorFactory} that creates a {@link DeepARTranslator} instance. */
public class DeepARTranslatorFactory implements TranslatorFactory {

    /** {@inheritDoc} */
    @Override
    public Set<Pair<Type, Type>> getSupportedTypes() {
        return Collections.singleton(new Pair<>(TimeSeriesData.class, Forecast.class));
    }

    /** {@inheritDoc} */
    @Override
    public Translator<?, ?> newInstance(
            Class<?> input, Class<?> output, Model model, Map<String, ?> arguments)
            throws TranslateException {
        if (!isSupported(input, output)) {
            throw new IllegalArgumentException("Unsupported input/output types.");
        }
        return DeepARTranslator.builder(arguments).build();
    }
}
