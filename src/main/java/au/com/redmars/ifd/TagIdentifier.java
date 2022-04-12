package au.com.redmars.ifd;

import java.util.HashMap;
import java.util.Map;

public enum TagIdentifier {
    InteropIndex(1),
    InteropVersion(2),
    //UnknownTag(64),
    NewSubfileType(254),
    SubfileType(255),
    ImageWidth(256),
    ImageLength(257),
    BitsPerSample(258),
    Compression(259),
    PhotometricInterpretation(262),
    Thresholding(270),
    Make(271),
    Model(272),
    StripOffsets(273),
    Orientation(274),
    SamplesPerPixel(277),
    RowsPerStrip(278),
    StripByteCounts(279),
    XResolution(282),
    YResolution(283),
    PlanarConfiguration(284),
    ResolutionUnit(296),
    Software(305),
    ModifyDate(306),
    Predictor(317),
    TileWidth(322),
    TileLength(323),
    TileOffsets(324),
    TileByteCounts(325),
    SubIFDs(330),
    ExtraSamples(338),
    SampleFormat(339),
    YCbCrCoefficients(529),
    YCbCrSubSampling(530),
    YCbCrPositioning(531),
    ReferenceBlackWhite(532),
    XMP(700),
    CFARepeatPatternDim(33421),
    CFAPattern2(33422),
    Copyright(33432),
    ExposureTime(33434),
    FNumber(33437),
    Exif_IFD(34665),
    ICC_Profile(34675), //TODO: ICC_Profile has sub entries
    ExposureProgram(34850),
    GPSInfo(34853),
    ISOSpeedRatings(34855),
    SensitivityType(34864),
    RecommendedExposureIndex(34866),
    ExifVersion(36864),
    DateTimeOriginal(36867),
    DateTimeDigitized(36868),
    OffsetTime(36880),
    OffsetTimeOriginal(36881),
    OffsetTimeDigitized(36882),
    ShutterSpeedValue(37377),
    ApertureValue(37378),
    BrightnessValue(37379),
    ExposureBiasValue(37380),
    MaxApertureValue(37381),
    SubjectDistance(37382),
    MeteringMode(37383),
    LightSource(37384),
    Flash(37385),
    FocalLength(37386),
    ImageNumber(37393),
    SubjectArea(37396),
    UserComment(37510),
    SubSecTimeOriginal(37521),
    SubSecTimeDigitized(37522),
    ColorSpace(40961),
    FocalPlaneXResolution(41486),
    FocalPlaneYResolution(41487),
    FocalPlaneResolutionUnit(41488),
    SensingMethod(41495),
    FileSource(41728),
    SceneType(41729),
    CFAPattern(41730),
    CustomRendered(41985),
    ExposureMode(41986),
    WhiteBalance(41987),
    DigitalZoomRatio(41988),
    FocalLengthIn35mmFilm(41989),
    SceneCaptureType(41990),
    GainControl(41991),
    Contrast(41992),
    Saturation(41993),
    Sharpness(41994),
    SubjectDistanceRange(41996),
    BodySerialNumber(42033),
    LensSpecification(42034),
    LensMake(42035),
    LensModel(42036),
    LensSerialNumber(42037),
    DNGVersion(50706),
    DNGBackwardVersion(50707),
    UniqueCameraModel(50708),
    LocalizedCameraModel(50709),
    CFAPlaneColor(50710),
    CFALayout(50711),
    LinearizationTable(50712),
    BlackLevelRepeatDim(50713),
    BlackLevel(50714),
    WhiteLevel(50717),
    DefaultScale(50718),
    DefaultCropOrigin(50719),
    DefaultCropSize(50720),
    ColorMatrix1(50721),
    ColorMatrix2(50722),
    CameraCalibration1(50723),
    CameraCalibration2(50724),
    AnalogBalance(50727),
    AsShotNeutral(50728),
    AsShotWhiteXY(50729),
    BaselineExposure(50730),
    BaselineNoise(50731),
    BaselineSharpness(50732),
    BayerGreenSplit(50733),
    LinearResponseLimit(50734),
    CameraSerialNumber(50735),
    DNGLensInfo(50736),
    ChromaBlurRadius(50737),
    AntiAliasStrength(50738),
    ShadowScale(50739),
    DNGPrivateData(50740),
    CalibrationIlluminant1(50778),
    CalibrationIlluminant2(50779),
    BestQualityScale(50780),
    RawDataUniqueID(50781),
    OriginalRawFileName(50827),
    ActiveArea(50829),
    ColorimetricReference(50879),
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
    OpcodeList2(51009),
    NoiseProfile(51041),
    OriginalDefaultFinalSize(51089),
    NewRawImageDigest(51111),
    CacheVersion(51114),
    DefaultUserCrop(51125),
    //Canon Maker Note Tags
    CanonCameraSettings(90001),
    CanonFocalLength(90002),
    CanonFlashInfo(90003),
    CanonShotInfo(90004),
    CanonPanorama(90005),
    CanonImageType(90006),
    CanonFirmwareVersion(90007),
    CanonFileNumber(90008),
    CanonOwnerName(90009),
    CanonUnknownD30(90010),
    CanonCameraInfo(90013), //TODO: CanonCameraInfo can represent several models and contains sub entries 
    CanonModelID(90016),
    ThumbnailImageValidArea(90019),
    CanonUnknownTag1(90024),
    CanonUnknownTag2(90025),
    DateStampMode(90028),
    FirmwareRevision(90030),
    Categories(90035),
    CanonAFInfo2(90038), //TODO: CanonAFInfo2 contains sub entries
    ContrastInfo(90039), //TODO: Canon ContractInfo contains sub entries
    ImageUniqueID(90040),
    CanonUnknownTag3(90045),
    CanonUnknownTag4(90046),
    FaceDetect3(90047),
    CanonUnknownTag5(90049),
    CanonUnknownTag6(90051),
    TimeInfo(90053), //TODO: Canon TimeInfo has sub entries
    CanonUnknownTag7(90054),
    BatteryType(90056),
    AFInfo3(90060), //TODO: Canon AFInfo3 has sub entries
    CanonUnknownTag8(90061),
    CanonUnknownTag9(90063),
    CanonUnknownTag10(90064),
    CanonUnknownTag11(90065),
    CanonFileInfo(90147), //TODO: CanonFileInfo has sub entries
    CanonLensModel(90149),
    SerialInfo(90150), //TODO: Can be a string or Canon SerialInfo sub entries
    DustRemovalData(90151),
    CropInfo(90152), //TODO: Canon CropInfo has sub entries
    CustomFunctions2(90153), //TODO: Canon CustomFunctions2 has sub entries
    AspectInfo(90154), //TODO: CanonAspectInfo has sub entries
    ProcessingInfo(90160), //TODO: Canon ProcessingInfo has sub entries
    MeasuredColor(90170), //TODO: Canon MeasuredColor has sub entries
    CanonColorSpace(90180),
    VRDOffset(90208),
    SensorInfo(90224), //TODO: Canon SensorInfo has sub entries
    ColorData(106385), //TODO: Canon ColorData has 13 model types and sub entries
    CRWParam(106386),
    Flavor(106389),
    PictureStyleUserDef(106392),
    PictureStylePC(106393),
    CustomPictureStyleFileName(106400),
    CanonUnknownTag12(106401),
    CanonUnknownTag13(106402),
    AFMicroAdj(106403), //TODO: Canon AFMicroAdj has sub entries
    VignettingCorr(106405), //TODO: Canon Vignetting Tags has sub entries
    VignettingCorr2(106406), //TODO: Canon VignettingCorr2 has sub entries
    LightingOpt(106408), //TODO: Canon LightlingOpt has sub entries
    LensInfo(106409), //TODO: Canon LensInfo has sub entries
    AmbienceInfo(106416), //TODO: Canon AmbienceInfo has sub entries
    CanonUnknownTag14(106419),
    FilterInfo(106420), //TODO: Canon FilterInfo has sub entries
    HDRInfo(106421), //TODO: Canon HDRInfo has sub entries
    CanonUnknownTag15(106423),
    CanonUnknownTag16(106427),
    CanonUnknownTag17(106428),
    CanonUnknownTag18(106430),
    //SAMSUNG Maker Note Tags
    MakerNoteVersion(110001),
    DeviceType(110002),
    UnknownSamsungTag(110012),
    UnknownSamsungTag1(110016),
    RawDataByteOrder(110064),
    RawDataCFAPattern(110080),
    FaceDetect(110256),
    //Olympus Maker Note Tags
    SpecialMode(160512),
    Quality(160513),
    Macro(160514),
    BWMode(160515),
    DigitalZoom(160516),
    FocalPlaneDiagonal(160517),
    LensDistortionParams(160518),
    CameraType(160519),
    TextInfo(160520), //TODO: Olympus TextInfo has sub entries
    CameraID(160521),
    UnknownOlympusTag(160549),
    PreCaptureFrames(160768),
    WhiteBoard(160769),
    OneTouchWB(160770),
    WhiteBalanceBracket(160771),
    WhiteBalanceBias(160772),
    UnknownOlympusTag1(160774),
    SensorArea(161024),
    OlympusBlackLevel(161025),
    UnknownOlympusTag2(161026),
    SceneMode(161027),
    SerialNumber(161028),
    Firmware(161029),
    DataDump(163840);

    private static final Map<Integer, TagIdentifier> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (TagIdentifier e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private TagIdentifier(Integer value) {
        this.value = value;
    }
    public static TagIdentifier valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
