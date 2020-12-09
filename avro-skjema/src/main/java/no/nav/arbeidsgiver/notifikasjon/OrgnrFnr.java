/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package no.nav.arbeidsgiver.notifikasjon;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

/** Mottas av dem med gitt fnr i konteksten av et bestemt orgnr */
@org.apache.avro.specific.AvroGenerated
public class OrgnrFnr extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4363445065078779151L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrgnrFnr\",\"namespace\":\"no.nav.arbeidsgiver.notifikasjon\",\"doc\":\"Mottas av dem med gitt fnr i konteksten av et bestemt orgnr\",\"fields\":[{\"name\":\"orgnr\",\"type\":\"string\"},{\"name\":\"fnr\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OrgnrFnr> ENCODER =
      new BinaryMessageEncoder<OrgnrFnr>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrgnrFnr> DECODER =
      new BinaryMessageDecoder<OrgnrFnr>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OrgnrFnr> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OrgnrFnr> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OrgnrFnr> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OrgnrFnr>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OrgnrFnr to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OrgnrFnr from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OrgnrFnr instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OrgnrFnr fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence orgnr;
   private java.lang.CharSequence fnr;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrgnrFnr() {}

  /**
   * All-args constructor.
   * @param orgnr The new value for orgnr
   * @param fnr The new value for fnr
   */
  public OrgnrFnr(java.lang.CharSequence orgnr, java.lang.CharSequence fnr) {
    this.orgnr = orgnr;
    this.fnr = fnr;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return orgnr;
    case 1: return fnr;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: orgnr = (java.lang.CharSequence)value$; break;
    case 1: fnr = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'orgnr' field.
   * @return The value of the 'orgnr' field.
   */
  public java.lang.CharSequence getOrgnr() {
    return orgnr;
  }


  /**
   * Sets the value of the 'orgnr' field.
   * @param value the value to set.
   */
  public void setOrgnr(java.lang.CharSequence value) {
    this.orgnr = value;
  }

  /**
   * Gets the value of the 'fnr' field.
   * @return The value of the 'fnr' field.
   */
  public java.lang.CharSequence getFnr() {
    return fnr;
  }


  /**
   * Sets the value of the 'fnr' field.
   * @param value the value to set.
   */
  public void setFnr(java.lang.CharSequence value) {
    this.fnr = value;
  }

  /**
   * Creates a new OrgnrFnr RecordBuilder.
   * @return A new OrgnrFnr RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder newBuilder() {
    return new no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder();
  }

  /**
   * Creates a new OrgnrFnr RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrgnrFnr RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder newBuilder(no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder other) {
    if (other == null) {
      return new no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder();
    } else {
      return new no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder(other);
    }
  }

  /**
   * Creates a new OrgnrFnr RecordBuilder by copying an existing OrgnrFnr instance.
   * @param other The existing instance to copy.
   * @return A new OrgnrFnr RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder newBuilder(no.nav.arbeidsgiver.notifikasjon.OrgnrFnr other) {
    if (other == null) {
      return new no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder();
    } else {
      return new no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder(other);
    }
  }

  /**
   * RecordBuilder for OrgnrFnr instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrgnrFnr>
    implements org.apache.avro.data.RecordBuilder<OrgnrFnr> {

    private java.lang.CharSequence orgnr;
    private java.lang.CharSequence fnr;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.orgnr)) {
        this.orgnr = data().deepCopy(fields()[0].schema(), other.orgnr);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.fnr)) {
        this.fnr = data().deepCopy(fields()[1].schema(), other.fnr);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing OrgnrFnr instance
     * @param other The existing instance to copy.
     */
    private Builder(no.nav.arbeidsgiver.notifikasjon.OrgnrFnr other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.orgnr)) {
        this.orgnr = data().deepCopy(fields()[0].schema(), other.orgnr);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.fnr)) {
        this.fnr = data().deepCopy(fields()[1].schema(), other.fnr);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'orgnr' field.
      * @return The value.
      */
    public java.lang.CharSequence getOrgnr() {
      return orgnr;
    }


    /**
      * Sets the value of the 'orgnr' field.
      * @param value The value of 'orgnr'.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder setOrgnr(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.orgnr = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'orgnr' field has been set.
      * @return True if the 'orgnr' field has been set, false otherwise.
      */
    public boolean hasOrgnr() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'orgnr' field.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder clearOrgnr() {
      orgnr = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'fnr' field.
      * @return The value.
      */
    public java.lang.CharSequence getFnr() {
      return fnr;
    }


    /**
      * Sets the value of the 'fnr' field.
      * @param value The value of 'fnr'.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder setFnr(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.fnr = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'fnr' field has been set.
      * @return True if the 'fnr' field has been set, false otherwise.
      */
    public boolean hasFnr() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'fnr' field.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.OrgnrFnr.Builder clearFnr() {
      fnr = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrgnrFnr build() {
      try {
        OrgnrFnr record = new OrgnrFnr();
        record.orgnr = fieldSetFlags()[0] ? this.orgnr : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.fnr = fieldSetFlags()[1] ? this.fnr : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrgnrFnr>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrgnrFnr>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrgnrFnr>
    READER$ = (org.apache.avro.io.DatumReader<OrgnrFnr>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.orgnr);

    out.writeString(this.fnr);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.orgnr = in.readString(this.orgnr instanceof Utf8 ? (Utf8)this.orgnr : null);

      this.fnr = in.readString(this.fnr instanceof Utf8 ? (Utf8)this.fnr : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.orgnr = in.readString(this.orgnr instanceof Utf8 ? (Utf8)this.orgnr : null);
          break;

        case 1:
          this.fnr = in.readString(this.fnr instanceof Utf8 ? (Utf8)this.fnr : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









