// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: respack.proto

package com.troila.cloud.respack.core.impl.proto;

public interface RespackOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Respack)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 status = 1;</code>
   */
  int getStatus();

  /**
   * <code>int32 err_code = 2;</code>
   */
  int getErrCode();

  /**
   * <code>.google.protobuf.Any data = 3;</code>
   */
  boolean hasData();
  /**
   * <code>.google.protobuf.Any data = 3;</code>
   */
  com.google.protobuf.Any getData();
  /**
   * <code>.google.protobuf.Any data = 3;</code>
   */
  com.google.protobuf.AnyOrBuilder getDataOrBuilder();
}
