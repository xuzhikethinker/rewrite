/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.rewrite.servlet.config.parameters;

import java.util.ArrayList;
import java.util.List;

import com.ocpsoft.rewrite.servlet.config.parameters.binding.Evaluation;
import com.ocpsoft.rewrite.servlet.parse.CapturingGroup;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class Parameter
{
   private final Parameterized<?> parent;
   private final CapturingGroup capture;

   private String pattern = "[^/]+";
   private final List<ParameterBinding> bindings = new ArrayList<ParameterBinding>();
   private final List<ParameterBinding> optionalBindings = new ArrayList<ParameterBinding>();

   public Parameter(final Parameterized<?> parent, final CapturingGroup capture)
   {
      this.parent = parent;
      this.capture = capture;

      // Set up default binding to evaluation context.
      this.bindings.add(Evaluation.property(getName()));
   }

   /*
    * Builders
    */
   public Parameter matches(final String pattern)
   {
      this.pattern = pattern;
      return this;
   }

   public Parameter bindsTo(final ParameterBinding binding)
   {
      this.bindings.add(binding);
      return this;
   }

   public Object and(final String param)
   {
      return parent.where(param);
   }

   public Parameter attemptBindTo(final ParameterBinding binding)
   {
      this.optionalBindings.add(binding);
      return this;
   }

   /*
    * Accessors
    */

   public CapturingGroup getCapture()
   {
      return capture;
   }

   @Override
   public String toString()
   {
      return "Parameter [capture=" + capture + ", pattern=" + pattern + "]";
   }

   public String getName()
   {
      return new String(capture.getCaptured());
   }

   public String getPattern()
   {
      return pattern;
   }

   public List<ParameterBinding> getBindings()
   {
      return bindings;
   }

   public List<ParameterBinding> getOptionalBindings()
   {
      return optionalBindings;
   }
}