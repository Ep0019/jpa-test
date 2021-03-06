package com.lhy.netty.protobuf;
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BookRequestMsg.proto

public final class BookRequestMsgBuffer {
  private BookRequestMsgBuffer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface BookRequestMsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:BookRequestMsg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int64 userId = 1;</code>
     *
     * <pre>
     *���Ͷ�Ʊ��Ϣ�û�
     * </pre>
     */
    boolean hasUserId();
    /**
     * <code>required int64 userId = 1;</code>
     *
     * <pre>
     *���Ͷ�Ʊ��Ϣ�û�
     * </pre>
     */
    long getUserId();

    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    boolean hasTrainNumber();
    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    java.lang.String getTrainNumber();
    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    com.google.protobuf.ByteString
        getTrainNumberBytes();

    /**
     * <code>required int32 code = 3;</code>
     *
     * <pre>
     *��ѯ����
     * </pre>
     */
    boolean hasCode();
    /**
     * <code>required int32 code = 3;</code>
     *
     * <pre>
     *��ѯ����
     * </pre>
     */
    int getCode();

    /**
     * <code>required int64 startTime = 4;</code>
     *
     * <pre>
     *����ʱ��
     * </pre>
     */
    boolean hasStartTime();
    /**
     * <code>required int64 startTime = 4;</code>
     *
     * <pre>
     *����ʱ��
     * </pre>
     */
    long getStartTime();
  }
  /**
   * Protobuf type {@code BookRequestMsg}
   */
  public  static final class BookRequestMsg extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:BookRequestMsg)
      BookRequestMsgOrBuilder {
    // Use BookRequestMsg.newBuilder() to construct.
    private BookRequestMsg(com.google.protobuf.GeneratedMessage.Builder builder) {
      super(builder);
    }
    private BookRequestMsg() {
      userId_ = 0L;
      trainNumber_ = "";
      code_ = 0;
      startTime_ = 0L;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private BookRequestMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              userId_ = input.readInt64();
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              trainNumber_ = bs;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              code_ = input.readInt32();
              break;
            }
            case 32: {
              bitField0_ |= 0x00000008;
              startTime_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw new RuntimeException(e.setUnfinishedMessage(this));
      } catch (java.io.IOException e) {
        throw new RuntimeException(
            new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this));
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return BookRequestMsgBuffer.internal_static_BookRequestMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return BookRequestMsgBuffer.internal_static_BookRequestMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              BookRequestMsgBuffer.BookRequestMsg.class, BookRequestMsgBuffer.BookRequestMsg.Builder.class);
    }

    private int bitField0_;
    public static final int USERID_FIELD_NUMBER = 1;
    private long userId_;
    /**
     * <code>required int64 userId = 1;</code>
     *
     * <pre>
     *���Ͷ�Ʊ��Ϣ�û�
     * </pre>
     */
    public boolean hasUserId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 userId = 1;</code>
     *
     * <pre>
     *���Ͷ�Ʊ��Ϣ�û�
     * </pre>
     */
    public long getUserId() {
      return userId_;
    }

    public static final int TRAINNUMBER_FIELD_NUMBER = 2;
    private volatile java.lang.Object trainNumber_;
    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    public boolean hasTrainNumber() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    public java.lang.String getTrainNumber() {
      java.lang.Object ref = trainNumber_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          trainNumber_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string trainNumber = 2;</code>
     *
     * <pre>
     *�𳵳���
     * </pre>
     */
    public com.google.protobuf.ByteString
        getTrainNumberBytes() {
      java.lang.Object ref = trainNumber_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        trainNumber_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CODE_FIELD_NUMBER = 3;
    private int code_;
    /**
     * <code>required int32 code = 3;</code>
     *
     * <pre>
     *��ѯ����
     * </pre>
     */
    public boolean hasCode() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 code = 3;</code>
     *
     * <pre>
     *��ѯ����
     * </pre>
     */
    public int getCode() {
      return code_;
    }

    public static final int STARTTIME_FIELD_NUMBER = 4;
    private long startTime_;
    /**
     * <code>required int64 startTime = 4;</code>
     *
     * <pre>
     *����ʱ��
     * </pre>
     */
    public boolean hasStartTime() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int64 startTime = 4;</code>
     *
     * <pre>
     *����ʱ��
     * </pre>
     */
    public long getStartTime() {
      return startTime_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasUserId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasTrainNumber()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasStartTime()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, userId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getTrainNumberBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, code_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeInt64(4, startTime_);
      }
      unknownFields.writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, userId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getTrainNumberBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, code_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, startTime_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static BookRequestMsgBuffer.BookRequestMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(BookRequestMsgBuffer.BookRequestMsg prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code BookRequestMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:BookRequestMsg)
        BookRequestMsgBuffer.BookRequestMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return BookRequestMsgBuffer.internal_static_BookRequestMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return BookRequestMsgBuffer.internal_static_BookRequestMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                BookRequestMsgBuffer.BookRequestMsg.class, BookRequestMsgBuffer.BookRequestMsg.Builder.class);
      }

      // Construct using BookRequestMsgBuffer.BookRequestMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        userId_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        trainNumber_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        code_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        startTime_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return BookRequestMsgBuffer.internal_static_BookRequestMsg_descriptor;
      }

      public BookRequestMsgBuffer.BookRequestMsg getDefaultInstanceForType() {
        return BookRequestMsgBuffer.BookRequestMsg.getDefaultInstance();
      }

      public BookRequestMsgBuffer.BookRequestMsg build() {
        BookRequestMsgBuffer.BookRequestMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public BookRequestMsgBuffer.BookRequestMsg buildPartial() {
        BookRequestMsgBuffer.BookRequestMsg result = new BookRequestMsgBuffer.BookRequestMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.userId_ = userId_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.trainNumber_ = trainNumber_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.code_ = code_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.startTime_ = startTime_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof BookRequestMsgBuffer.BookRequestMsg) {
          return mergeFrom((BookRequestMsgBuffer.BookRequestMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(BookRequestMsgBuffer.BookRequestMsg other) {
        if (other == BookRequestMsgBuffer.BookRequestMsg.getDefaultInstance()) return this;
        if (other.hasUserId()) {
          setUserId(other.getUserId());
        }
        if (other.hasTrainNumber()) {
          bitField0_ |= 0x00000002;
          trainNumber_ = other.trainNumber_;
          onChanged();
        }
        if (other.hasCode()) {
          setCode(other.getCode());
        }
        if (other.hasStartTime()) {
          setStartTime(other.getStartTime());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasUserId()) {
          return false;
        }
        if (!hasTrainNumber()) {
          return false;
        }
        if (!hasCode()) {
          return false;
        }
        if (!hasStartTime()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        BookRequestMsgBuffer.BookRequestMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (BookRequestMsgBuffer.BookRequestMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private long userId_ ;
      /**
       * <code>required int64 userId = 1;</code>
       *
       * <pre>
       *���Ͷ�Ʊ��Ϣ�û�
       * </pre>
       */
      public boolean hasUserId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 userId = 1;</code>
       *
       * <pre>
       *���Ͷ�Ʊ��Ϣ�û�
       * </pre>
       */
      public long getUserId() {
        return userId_;
      }
      /**
       * <code>required int64 userId = 1;</code>
       *
       * <pre>
       *���Ͷ�Ʊ��Ϣ�û�
       * </pre>
       */
      public Builder setUserId(long value) {
        bitField0_ |= 0x00000001;
        userId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 userId = 1;</code>
       *
       * <pre>
       *���Ͷ�Ʊ��Ϣ�û�
       * </pre>
       */
      public Builder clearUserId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        userId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object trainNumber_ = "";
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public boolean hasTrainNumber() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public java.lang.String getTrainNumber() {
        java.lang.Object ref = trainNumber_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            trainNumber_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public com.google.protobuf.ByteString
          getTrainNumberBytes() {
        java.lang.Object ref = trainNumber_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          trainNumber_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public Builder setTrainNumber(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        trainNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public Builder clearTrainNumber() {
        bitField0_ = (bitField0_ & ~0x00000002);
        trainNumber_ = getDefaultInstance().getTrainNumber();
        onChanged();
        return this;
      }
      /**
       * <code>required string trainNumber = 2;</code>
       *
       * <pre>
       *�𳵳���
       * </pre>
       */
      public Builder setTrainNumberBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        trainNumber_ = value;
        onChanged();
        return this;
      }

      private int code_ ;
      /**
       * <code>required int32 code = 3;</code>
       *
       * <pre>
       *��ѯ����
       * </pre>
       */
      public boolean hasCode() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required int32 code = 3;</code>
       *
       * <pre>
       *��ѯ����
       * </pre>
       */
      public int getCode() {
        return code_;
      }
      /**
       * <code>required int32 code = 3;</code>
       *
       * <pre>
       *��ѯ����
       * </pre>
       */
      public Builder setCode(int value) {
        bitField0_ |= 0x00000004;
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 code = 3;</code>
       *
       * <pre>
       *��ѯ����
       * </pre>
       */
      public Builder clearCode() {
        bitField0_ = (bitField0_ & ~0x00000004);
        code_ = 0;
        onChanged();
        return this;
      }

      private long startTime_ ;
      /**
       * <code>required int64 startTime = 4;</code>
       *
       * <pre>
       *����ʱ��
       * </pre>
       */
      public boolean hasStartTime() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required int64 startTime = 4;</code>
       *
       * <pre>
       *����ʱ��
       * </pre>
       */
      public long getStartTime() {
        return startTime_;
      }
      /**
       * <code>required int64 startTime = 4;</code>
       *
       * <pre>
       *����ʱ��
       * </pre>
       */
      public Builder setStartTime(long value) {
        bitField0_ |= 0x00000008;
        startTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 startTime = 4;</code>
       *
       * <pre>
       *����ʱ��
       * </pre>
       */
      public Builder clearStartTime() {
        bitField0_ = (bitField0_ & ~0x00000008);
        startTime_ = 0L;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:BookRequestMsg)
    }

    // @@protoc_insertion_point(class_scope:BookRequestMsg)
    private static final BookRequestMsgBuffer.BookRequestMsg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new BookRequestMsgBuffer.BookRequestMsg();
    }

    public static BookRequestMsgBuffer.BookRequestMsg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    public static final com.google.protobuf.Parser<BookRequestMsg> PARSER =
        new com.google.protobuf.AbstractParser<BookRequestMsg>() {
      public BookRequestMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        try {
          return new BookRequestMsg(input, extensionRegistry);
        } catch (RuntimeException e) {
          if (e.getCause() instanceof
              com.google.protobuf.InvalidProtocolBufferException) {
            throw (com.google.protobuf.InvalidProtocolBufferException)
                e.getCause();
          }
          throw e;
        }
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BookRequestMsg> getParserForType() {
      return PARSER;
    }

    public BookRequestMsgBuffer.BookRequestMsg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_BookRequestMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_BookRequestMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024BookRequestMsg.proto\"V\n\016BookRequestMsg" +
      "\022\016\n\006userId\030\001 \002(\003\022\023\n\013trainNumber\030\002 \002(\t\022\014\n" +
      "\004code\030\003 \002(\005\022\021\n\tstartTime\030\004 \002(\003B\026B\024BookRe" +
      "questMsgBuffer"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_BookRequestMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BookRequestMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_BookRequestMsg_descriptor,
        new java.lang.String[] { "UserId", "TrainNumber", "Code", "StartTime", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
