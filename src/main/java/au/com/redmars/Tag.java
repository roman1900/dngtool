package au.com.redmars;

import java.util.HashMap;
import java.util.Map;

public enum Tag {
    NewSubfileType(254),
    SubfileType(255),
    ImageWidth(256),
    ImageLength(257),
    BitsPerSample(258),
    Compression(259),
    PhotometricInterpretation(262),
    Make(271),
    Model(272),
    StripOffsets(273),
    Orientation(274),
    SamplesPerPixel(277),
    RowsPerStrip(278),
    StripByteCounts(279),
    PlanarConfiguration(284),
    Software(305),
    DateTime(306),
    SubIFDs(330),
    XMP(700),
    Exif_IFD(34665),
    ImageNumber(37393),
    DNGVersion(50706),
    DNGBackwardVersion(50707),
    UniqueCameraModel(50708),
    ColorMatrix1(50721),
    ColorMatrix2(50722),
    CameraCalibration1(50723),
    CameraCalibration2(50724),
    AnalogBalance(50727),
    AsShotNeutral(50728),
    BaselineExposure(50730),
    BaselineNoise(50731),
    BaselineSharpness(50732),
    LinearResponseLimit(50734),
    CameraSerialNumber(50735),
    LensInfo(50736),
    ShadowScale(50739),
    DNGPrivateData(50740),
    CalibrationIlluminant1(50778),
    CalibrationIlluminant2(50779),
    RawDataUniqueID(50781),
    OriginalRawFileName(50827),
    CameraCalibrationSignature(50931),
    ProfileCalibrationSignature(50932),
    ProfileName(50936),
    ProfileHueSatMapDims(50937),
    ProfileHueSatMapData1(50938),
    ProfileHueSatMapData2(50939),
    ProfileEmbedPolicy(50941),
    ProfileCopyright(50942),
    ForwardMatrix1(50964),
    ForwardMatrix2(50965),
    PreviewApplicationName(50966),
    PreviewApplicationVersion(50967),
    PreviewSettingsDigest(50969),
    PreviewColorSpace(50970),
    PreviewDateTime(50971),
    ProfileLookTableDims(50981),
    ProfileLookTableData(50982),
    NoiseProfile(51041),
    NewRawImageDigest(51111);

    private static final Map<Integer, Tag> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (Tag e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private Tag(Integer value) {
        this.value = value;
    }
    public static Tag valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
